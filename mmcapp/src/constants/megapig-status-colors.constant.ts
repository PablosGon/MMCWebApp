import { MegapigStatus } from "@/enums/megapig-status.enum";

export const MegapigStatusColors = 
[
  { status: MegapigStatus.Compliant, color: 'bg-green-300', text: 'Correcto', textColor: 'text-green-300' },
  { status: MegapigStatus.Minor_Penalty, color: 'bg-yellow-300', text: 'Penalización leve', textColor: 'text-yellow-300' },
  { status: MegapigStatus.Major_Penalty, color: 'bg-red-400', text: 'Penalización grave', textColor: 'text-red-400' },
  { status: MegapigStatus.No_Penalty, color: 'bg-gray-300', text: 'Sin penalización', textColor: 'text-gray-300' },
]